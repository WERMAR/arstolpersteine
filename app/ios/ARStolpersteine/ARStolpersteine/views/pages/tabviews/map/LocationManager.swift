//
//  LocationManager.swift
//  ARStolpersteine
//
//  Created by Marcel Wernisch on 18.11.23.
//

import MapKit

final class LocationManager: NSObject, ObservableObject {
    private let locationManager = CLLocationManager()
    
    private var mapDelegate: MapDelegate?
    
    @Published var region = MKCoordinateRegion(
        center: .init(latitude: 50.036666, longitude: 10.515015),
        span: .init(latitudeDelta: 0.2, longitudeDelta: 0.2)
    )
    
    override init() {
        super.init()
        self.locationManager.delegate = self
        self.locationManager.desiredAccuracy = kCLLocationAccuracyBest
        self.setup()
    }
    
    func startUpdatingPosition(_ delegate: MapDelegate) {
        print("LOCATIONMANAGER START UPDATING PERFORM")
        self.mapDelegate = delegate
        self.locationManager.startUpdatingLocation()
    }
    
    /**
     Setting Up LocationManager. Checks for Autorization-Status, if not already authorized then a request will be start. Also the Location will be determined in the background for less loading time.
     */
    func setup() {
        switch locationManager.authorizationStatus {
            // AUTH true -> Request Current Location
        case .authorizedWhenInUse:
            locationManager.requestLocation()
            // AUTH false -> Request Auth and Update Location meanwhile
        case .notDetermined:
            locationManager.startUpdatingLocation()
            locationManager.requestWhenInUseAuthorization()
        default:
            break
        }
    }
}

extension LocationManager: CLLocationManagerDelegate {
    func locationManagerDidChangeAuthorization(_ manager: CLLocationManager) {
        guard .authorizedWhenInUse == manager.authorizationStatus else { return }
        locationManager.requestLocation()
    }
    
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        print("Something went wrong: \(error)")
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        locations.last.map {
            region = MKCoordinateRegion(
                center: $0.coordinate,
                span: .init(latitudeDelta: 0.01, longitudeDelta: 0.01)
            )
        }
        self.mapDelegate?.regionDidChanged(region)
        NSLog("Region: \(region.center)")
    }
    
    func stopUpdatingPosition() {
        print("LOCATIONMANAGER STOP UPDATING PERFORM")
        self.locationManager.stopUpdatingLocation()
    }
}
