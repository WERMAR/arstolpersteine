//
//  MapViewModel.swift
//  ARStolpersteine
//
//  Created by Marcel Wernisch on 17.11.23.
//

import SwiftUI
import MapKit


enum MapDetails {
    static let startingLocation = CLLocationCoordinate2D(latitude: 52.516, longitude: 13.378)
    static let defaultSpan = MKCoordinateSpan(latitudeDelta: 0.5, longitudeDelta: 0.5)
}

final class MapViewModel: NSObject, ObservableObject, CLLocationManagerDelegate {
    
    @Published var region = MKCoordinateRegion(center: MapDetails.startingLocation , span: MapDetails.defaultSpan)
    
    var locationManager: CLLocationManager?
    
    private var mapDelegate: MapDelegate?
    
    func startUpdatingPosition(_ delegate: MapDelegate) {
        print("LOCATIONMANAGER START UPDATING PERFORM")
        self.mapDelegate = delegate
        self.locationManager?.startUpdatingLocation()
    }
    
    func checkIfLocationServicesIsEnabled() {
        if CLLocationManager.locationServicesEnabled() {
            locationManager = CLLocationManager()
            locationManager!.delegate = self
        } else {
            // Show Alert
        }
    }
    
    func stopUpdatingPosition() {
        print("LOCATIONMANAGER STOP UPDATING PERFORM")
        self.locationManager?.stopUpdatingLocation()
    }
    
    private func checkLocationAuthorization() {
        guard let locationManager = locationManager else {
            return
        }
        
        switch locationManager.authorizationStatus {
        case .notDetermined:
            locationManager.requestAlwaysAuthorization()
        case .restricted:
            print("Location is restricted")
            locationManager.requestAlwaysAuthorization()
        case .denied:
            print("Location is denied")
            locationManager.requestAlwaysAuthorization()
        case .authorizedAlways, .authorizedWhenInUse:
            region = MKCoordinateRegion(center: locationManager.location!.coordinate, span: MapDetails.defaultSpan)
            NSLog("Region: \(region.center)")
        @unknown default:
            break;
        }
    }
    
    func locationManagerDidChangeAuthorization(_ manager: CLLocationManager) {
        checkLocationAuthorization()
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        region = MKCoordinateRegion(center: locations.first!.coordinate, span: MapDetails.defaultSpan)
        self.mapDelegate?.regionDidChanged(region)
        NSLog("Region: \(region.center)")
    }
}
