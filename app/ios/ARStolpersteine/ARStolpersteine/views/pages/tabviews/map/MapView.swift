//
//  MapView.swift
//  ARStolpersteine
//
//  Created by Marcel Wernisch on 17.11.23.
//

import SwiftUI
import MapKit


struct MapView: View {
    
    @StateObject var manager = LocationManager()
    @State private var updatedRegion: MKCoordinateRegion? = nil
    
    
    var body: some View {
        ZStack{
            Map(coordinateRegion: $manager.region, 
                showsUserLocation: true)
            .edgesIgnoringSafeArea(.top)
        }
    }
}

#Preview {
    MapView()
}
