//
//  CompassRangeEnum.swift
//  ARStolpersteine
//
//  Created by Marcel Wernisch on 24.01.24.
//

import Foundation
import SwiftUI


struct CompassRangeEnum {
    static func image(for value: Double, northFileURL: String?, southFileURL: String?, eastFileURL: String?, westFileURL: String?) -> RemoteImage? {
        switch value {
        case 0...45:
            if (northFileURL == nil) {
                return nil
            }
            return RemoteImage(placeholderImage: Image(systemName: "photo"), fileUrl: northFileURL!)
        case 45...135:
            if (eastFileURL == nil) {
                return nil
            }
            return RemoteImage(placeholderImage: Image(systemName: "photo"), fileUrl: eastFileURL!)
        case 135...225:
            if (southFileURL == nil) {
                return nil
            }
            return RemoteImage(placeholderImage: Image(systemName: "photo"), fileUrl: southFileURL!)
        case 225...315:
            if (westFileURL == nil) {
                return nil
            }
            return RemoteImage(placeholderImage: Image(systemName: "photo"), fileUrl: westFileURL!)
        case 315...360:
            if (northFileURL == nil) {
                return nil
            }
            return RemoteImage(placeholderImage: Image(systemName: "photo"), fileUrl: northFileURL!)
        default:
           fatalError("Compass Value was is not a valid compass number")
        }
    }
}
