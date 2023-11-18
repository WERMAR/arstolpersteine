//
//  MapDelegate.swift
//  ARStolpersteine
//
//  Created by Marcel Wernisch on 17.11.23.
//


import Foundation
import MapKit

protocol MapDelegate {
    
    func regionDidChanged(_ region: MKCoordinateRegion)
}

