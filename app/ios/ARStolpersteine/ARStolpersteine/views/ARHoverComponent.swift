//
//  ARHoverComponent.swift
//  ARStolpersteine
//
//  Created by Marcel Wernisch on 22.01.24.
//

import SwiftUI

struct ARHoverComponent: View {
    
    
    
    var body: some View {
        ZStack {
            // Blurred rectangle
            Rectangle()
                .fill(Color.darkerGray)
                .blur(radius: 10) // Adjust the blur radius as needed
                .frame(width: 300, height: 450)
                .opacity(0.8)
            VStack {
                Image(systemName: "star.fill") // Replace with your image
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 50, height: 50) // Adjust image size
                
                Text("Your Text Here")
                    .foregroundColor(.white)
            }
        }
    }
}

#Preview {
    ARHoverComponent()
}
