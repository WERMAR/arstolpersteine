//
//  MapView.swift
//  ARStolpersteine
//
//  Created by Marcel Wernisch on 17.11.23.
//

import SwiftUI
import MapKit


struct MapView: View, MapDelegate {
    @StateObject private var viewModel = MapViewModel()
    @State private var updatedRegion: MKCoordinateRegion? = nil
    
    
    var body: some View {
        ZStack{
            Map(coordinateRegion: $viewModel.region, showsUserLocation: true)
                .edgesIgnoringSafeArea(.top)
        }.onAppear {
            viewModel.checkIfLocationServicesIsEnabled()
            viewModel.startUpdatingPosition(self)
        }
        .onDisappear {
            self.viewModel.stopUpdatingPosition()
        }
    }
    
    func regionDidChanged(_ region: MKCoordinateRegion) {
        self.updatedRegion = region
    }
}

#Preview {
    MapView()
}
