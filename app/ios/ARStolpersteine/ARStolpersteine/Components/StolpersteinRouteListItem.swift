//
//  StolpersteinRouteListItem.swift
//  ARStolpersteine
//
//  Created by Marcel Wernisch on 24.01.24.
//

import SwiftUI
import OpenAPIRuntime
import OpenAPIURLSession
import MapKit

struct StolpersteinRouteListItem: View {
    
    let stolperstein:  StolpersteinWithDistance
    
    init(_ stolperstein: StolpersteinWithDistance) {
        self.stolperstein = stolperstein
    }
    
    var body: some View {
        VStack(spacing: 50) {
            HStack {
                VStack (alignment: .leading) {
                    Text(String("Name des Stolpersteins")).bold().underline()
                    Text(String("\(stolperstein.stolperstein.victim!.firstname!) \(stolperstein.stolperstein.victim!.lastname!)"))
                }
                Spacer()
                HStack {
                    
                    VStack (alignment: .trailing) {
                        Text(String("Distanz")).bold().underline()
                        Text(String("\(String(format: "%.2f", stolperstein.distance)) Meter"))
                    }
                    Button(action: {
                        NSLog(String("Route to \(stolperstein.stolperstein.id!)"))
                        // MARK: Open Map for Routing to next Stolperstein
                        let latitude = Double(stolperstein.stolperstein.position!.lat!)
                        let longitude = Double(stolperstein.stolperstein.position!.lng!)
                        let regionDistance:CLLocationDistance = 10000
                        let coordinates = CLLocationCoordinate2DMake(latitude, longitude)
                        let regionSpan = MKCoordinateRegion(center: coordinates, latitudinalMeters: regionDistance, longitudinalMeters: regionDistance)
                        let options = [
                            MKLaunchOptionsMapCenterKey: NSValue(mkCoordinate: regionSpan.center),
                            MKLaunchOptionsMapSpanKey: NSValue(mkCoordinateSpan: regionSpan.span)
                        ]
                        let placemark = MKPlacemark(coordinate: coordinates, addressDictionary: nil)
                        let mapItem = MKMapItem(placemark: placemark)
                        mapItem.name = String("\(stolperstein.stolperstein.victim!.firstname!) \(stolperstein.stolperstein.victim!.lastname!)")
                        mapItem.openInMaps(launchOptions: options)
                    }) {
                        Image(systemName: "mappin.and.ellipse")
                    }
                }
                
            }
        }
        Divider()
    }
}
