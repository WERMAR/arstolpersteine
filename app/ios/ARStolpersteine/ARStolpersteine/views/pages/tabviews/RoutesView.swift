//
//  RoutesView.swift
//  ARStolpersteine
//
//  Created by Marcel Wernisch on 17.11.23.
//

import SwiftUI

struct RoutesView: View {
    var body: some View {
        ZStack {
            Color.backgroundColor.ignoresSafeArea()
            RemoteImage(placeholderImage: Image(systemName: "photo"), fileUrl: String("4383773892721109878/image2.jpeg"))
        }
    }
}

#Preview {
    RoutesView()
}
