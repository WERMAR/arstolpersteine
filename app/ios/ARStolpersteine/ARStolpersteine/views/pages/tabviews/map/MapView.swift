//
//  MapView.swift
//  ARStolpersteine
//
//  Created by Marcel Wernisch on 17.11.23.
//

import SwiftUI
import MapKit
import OpenAPIRuntime
import OpenAPIURLSession

struct MapView: View, MapDelegate {
    
    @StateObject var manager = LocationManager()
    @State private var updatedRegion: MKCoordinateRegion? = nil
    @State private var annotationItems = [StolpersteinPin]()
    
    
    var body: some View {
        ZStack{
            Map(coordinateRegion: $manager.region,
                showsUserLocation: true, annotationItems: annotationItems) { place in
                MapMarker(coordinate: place.coordinate, tint: Color.purple)
            }
            .edgesIgnoringSafeArea(.top)
        }.onAppear {
            manager.startUpdatingPosition(self)
        }
        .onDisappear {
            manager.stopUpdatingPosition()
        }
    }
    
    let client: Client
    
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
    
    func regionDidChanged(_ region: MKCoordinateRegion) {
        NSLog("Region Changed")
        self.updatedRegion = region
        self.loadData()
    }
    
    func loadStolpersteine() async throws {
        NSLog("Load Stolpersteine")
        let currentLocation = (long: self.updatedRegion!.center.longitude, lat: self.updatedRegion!.center.latitude)
        
        let response = try await client.getStolpersteineForUser(query: Operations.getStolpersteineForUser.Input.Query(radius: 0.002, latUser: Float(currentLocation.lat), lngUser: Float(currentLocation.long)))
        switch response {
        case let .ok(okResponse):
            switch okResponse.body {
            case .json(let json):
                self.addPinsToMap(json)
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
    
    private func addPinsToMap(_ data: [Components.Schemas.StolpersteinPositionDto]) {
        for stolpersteinPosition in data {
            let pin = StolpersteinPin(coordinate: CLLocationCoordinate2D(latitude: Double(stolpersteinPosition.position!.lat!), longitude: Double(stolpersteinPosition.position!.lng!)))
            
            self.annotationItems.append(pin)
        }
    }
}

struct StolpersteinPin: Identifiable {
    let id = UUID()
    var coordinate: CLLocationCoordinate2D
}

#Preview {
    MapView()
}
