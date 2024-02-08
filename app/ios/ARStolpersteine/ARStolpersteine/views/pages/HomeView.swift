//
//  HomeView.swift
//  ARStolpersteine
//
//  Created by Marcel Wernisch on 17.11.23.
//

import SwiftUI

struct HomeView: View {
    @State private var selection = 2
    
    var body: some View {
        VStack {
            TabView(selection: $selection) {
                RoutesView()
                    .tabItem {
                        Image(systemName: "arrow.triangle.branch")
                        Text("Routen")
                    }.tag(1)
                MapView()
                    .tabItem {
                        Image(systemName: "map.fill")
                        Text("Map")
                    }.tag(2)
                CameraARView()
                    .tabItem {
                        Image(systemName: "arkit")
                        Text("AR-Stolpersteine")
                    }.tag(3)
            }
        }
    }
}
