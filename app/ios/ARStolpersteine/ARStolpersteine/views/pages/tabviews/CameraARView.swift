//
//  CameraARView.swift
//  ARStolpersteine
//
//  Created by Marcel Wernisch on 17.11.23.
//

import SwiftUI
import MapKit
import RealityKit
import OpenAPIRuntime
import OpenAPIURLSession


struct CameraARView: View, MapDelegate {
    
    // MARK: View Configuration
    var body: some View {
        ARViewContainer().edgesIgnoringSafeArea(.top)
    }
    
    // MARK: Code Section
    private let client: Client
    @State private var updatedRegion: MKCoordinateRegion? = nil
    @State private var arPoints = [ARPoint]()
    
    init() {
        self.client = Client(serverURL: try! Servers.server1(), transport: URLSessionTransport())
        loadData()
    }
    
    func loadData() {
        if (self.updatedRegion != nil) {
            Task {
                try await loadStolpersteine()
                updateAnchorView()
            }
        }
    }
  
    
    // MARK: AR-Point / Moving Code
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
    }
    
    
    
    // MARK: AR View Code
    func updateAnchorView() {
        
    }
    
    
}

struct ARViewContainer: UIViewRepresentable {
    
    func makeUIView(context: Context) -> ARView {
        
        let arView = ARView(frame: .zero)

        // Create a cube model
        let mesh = MeshResource.generateBox(size: 0.1, cornerRadius: 0.005)
        let material = SimpleMaterial(color: .gray, roughness: 0.15, isMetallic: true)
        let model = ModelEntity(mesh: mesh, materials: [material])

        // Create horizontal plane anchor for the content
        let anchor = AnchorEntity(.plane(.horizontal, classification: .any, minimumBounds: SIMD2<Float>(0.2, 0.2)))
        anchor.children.append(model)

        // Add the horizontal plane anchor to the scene
        arView.scene.anchors.append(anchor)

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
