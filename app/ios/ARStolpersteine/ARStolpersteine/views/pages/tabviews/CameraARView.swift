//
//  CameraARView.swift
//  ARStolpersteine
//
//  Created by Marcel Wernisch on 17.11.23.
//

import SwiftUI
import MapKit
import ARKit
import RealityKit
import OpenAPIRuntime
import OpenAPIURLSession


struct CameraARView: View, MapDelegate {
    @ObservedObject var compassHeading = CompassHeading()
    @StateObject var manager = LocationManager()

    // MARK: View Configuration
    var body: some View {
        ZStack {
            ARViewContainer().edgesIgnoringSafeArea(.top)
            CompassRangeEnum.image(for: self.compassHeading.degrees, northFileURL: getImageForKeyURL(DirectionKeys.NORTH), southFileURL: getImageForKeyURL(DirectionKeys.SOUTH), eastFileURL: getImageForKeyURL(DirectionKeys.EAST), westFileURL: getImageForKeyURL(DirectionKeys.WEST))
        }.onAppear {
            manager.startUpdatingPosition(self)
        }
        .onDisappear {
            manager.stopUpdatingPosition()
        }
    }
    
    // MARK: Code Section
    private let client: Client
    @State private var updatedRegion: MKCoordinateRegion? = nil
    @State private var arPoints = [ARPoint]()
    @State var currentActiveStolperstein: Components.Schemas.StolpersteineResponseDto? = nil
    
    init() {
        self.client = Client(serverURL: try! Servers.server1(), transport: URLSessionTransport())
        loadData()
    }
    
    func loadData() {
        if (self.updatedRegion != nil) {
            Task {
                try await loadStolpersteine()
            }
        }
    }
    
    
    // MARK: AR-Point / Moving Code
    func regionDidChanged(_ region: MKCoordinateRegion) {
        NSLog("Region Changed")
        self.updatedRegion = region
        if (!stolpersteineInRadius()) {
            self.loadData()
        }
    }
    
    func loadStolpersteine() async throws {
        NSLog("Load Stolpersteine")
        let currentLocation = (long: self.updatedRegion!.center.longitude, lat: self.updatedRegion!.center.latitude)
        
        let response = try await client.getStolpersteineForUser(query: Operations.getStolpersteineForUser.Input.Query(radius: 0.002, latUser: Float(currentLocation.lat), lngUser: Float(currentLocation.long)))
        switch response {
        case let .ok(okResponse):
            switch okResponse.body {
            case .json(let json):
                self.saveResult(json)
            }
        case .undocumented(statusCode: let statusCode, _):
            NSLog("StatusCode: \(statusCode)")
        case .badRequest(_):
            NSLog("StatusCode: \(400)")
        case .notFound(_):
            NSLog("StatusCode: \(404)")
        case .internalServerError(_):
            NSLog("StatusCode: \(500)")
        }
    }
    
    private func saveResult(_ data: [Components.Schemas.StolpersteinPositionDto]) {
        for stolpersteinPosition in data {
            let arPoint = ARPoint(pin: CLLocationCoordinate2D(latitude: Double(stolpersteinPosition.position!.lat!), longitude: Double(stolpersteinPosition.position!.lng!)), data: stolpersteinPosition)
            
            self.arPoints.append(arPoint)
        }
        self.updateActiveStolperstein()
    }
    
    private func updateActiveStolperstein() {
        let nearestARPin = getNearestStolperstein()
        
        // fetch StolpersteinData and and prepare Images
        Task {
            try await loadStolpersteinInformation(nearestARPin.id ?? -1)
        }
    }
    
    private func loadStolpersteinInformation(_ stolpersteinId: Int64) async throws {
        if (stolpersteinId == -1) {
            return
        }
        let response = try await client.getStolpersteinForID(path: Operations.getStolpersteinForID.Input.Path(stolpersteinId: stolpersteinId))
        switch response {
        case let .ok(okResponse):
            switch okResponse.body {
            case .json(let json):
                self.saveStolpersteinResult(json)
            }
        case .undocumented(statusCode: let statusCode, _):
            NSLog("StatusCode: \(statusCode)")
        case .badRequest(_):
            NSLog("StatusCode: \(400)")
        case .notFound(_):
            NSLog("StatusCode: \(404)")
        case .internalServerError(_):
            NSLog("StatusCode: \(500)")
            
        }
    }
    
    private func saveStolpersteinResult(_ json: Components.Schemas.StolpersteineResponseDto) {
        self.currentActiveStolperstein = json
    }
    
    private func stolpersteineInRadius() -> Bool {
        let myLocation = CLLocation(latitude: self.updatedRegion!.center.latitude, longitude: self.updatedRegion!.center.longitude)
        var isPointAvailable = false;
        for arPoint in arPoints {
            if (!isPointAvailable) {
                
                let arPointLocation = CLLocation(latitude: arPoint.pin.latitude, longitude: arPoint.pin.longitude)
                let distance = arPointLocation.distance(from: myLocation)
                switch (distance) {
                case 0...5:
                    isPointAvailable = true
                default:
                    isPointAvailable = false
                }
            }
        }
        return isPointAvailable;
    }
    
    
    private func getNearestStolperstein() -> Components.Schemas.StolpersteinPositionDto {
        let myLocation = getCLLocationOfPoint(self.updatedRegion!)
        var nearestStolperstein: ARPoint? = nil;
        var previousDistance = -1.0;
        for arPoint in arPoints {
            let arPointLocation = getCLLocationOfPoint(arPoint.pin)
            let distance = arPointLocation.distance(from: myLocation)
            if (distance <= 5 && previousDistance == -1.0) {
                previousDistance = distance
                nearestStolperstein = arPoint
            } else if (distance <= 5 && distance < previousDistance) {
                previousDistance = distance
                nearestStolperstein = arPoint;
            }
        }
        return nearestStolperstein!.data;
    }
    
    private func getCLLocationOfPoint(_ coordinates: CLLocationCoordinate2D) -> CLLocation {
        return CLLocation(latitude: coordinates.latitude, longitude: coordinates.longitude)
    }
    
    
    private func getCLLocationOfPoint(_ coordinates: MKCoordinateRegion) -> CLLocation {
       return CLLocation(latitude: coordinates.center.latitude, longitude: coordinates.center.longitude)
    }
    
    private func getImageForKeyURL(_ key: DirectionKeys) -> String? {
        if (self.currentActiveStolperstein == nil) {
            return nil
        }
        for photo in self.currentActiveStolperstein!.photos ?? [] {
            if (photo.heading == key.rawValue) {
                return photo.resourceUrl
            }
        }
        return nil
    }
}

struct ARViewContainer: UIViewRepresentable {
    
    func makeUIView(context: Context) -> ARView {
        
        let arView = ARView(frame: .zero)
        
        let configuration = ARWorldTrackingConfiguration()
        arView.session.run(configuration)
        
        return arView
        
    }
    
    func updateUIView(_ uiView: ARView, context: Context) {
        
    }
    
}


struct ARPoint {
    var pin: CLLocationCoordinate2D
    var data: Components.Schemas.StolpersteinPositionDto
}

#Preview {
    CameraARView()
}
