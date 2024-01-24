//
//  RemoteImage.swift
//  ARStolpersteine
//
//  Created by Marcel Wernisch on 24.01.24.
//

import SwiftUI
import OpenAPIRuntime
import OpenAPIURLSession

struct RemoteImage: View {
    
    @EnvironmentObject var remoteImageCache: RemoteImageCache
    @State var uiImage: UIImage?
    
    // Public API Client
    private let client: Client
    let placeholderImage: Image
    let fileURL: String
    
    init(placeholderImage: Image, fileUrl: String) {
        self.client = Client(serverURL: try! Servers.server1(), transport: URLSessionTransport())
        self.placeholderImage = placeholderImage
        self.fileURL = fileUrl
    }
    
    var body: some View {
        if let uiImage = self.uiImage {
            Image(uiImage: uiImage)
                .resizable()
                .scaledToFill()
                .frame(maxWidth: 400, maxHeight: 600)
                .background(Color.white)
                .clipShape(RoundedRectangle(cornerRadius: 10))
        } else {
            placeholderImage
                .resizable()
                .frame(width: 150, height: 150)
                .clipShape(Circle())
                .onAppear(perform: getImage)
        }
    }
    
    private func getImage() {
    
        if let cachedImage = self.remoteImageCache.getImage(for: self.fileURL) {
            NSLog(String("Used cache for File \(self.fileURL)"))
            self.uiImage = cachedImage
        } else {
            
            downloadImageData() { imageData in
                guard
                    let imageData = imageData,
                    let uiImage = UIImage(data: imageData) else {
                        self.uiImage = nil
                        return
                    }
                
                self.remoteImageCache.cache(uiImage, for: self.fileURL)
                
                DispatchQueue.main.async {
                    self.uiImage = uiImage
                }
                
            }
        }
    }
    
    
    func downloadImageData(completion: @escaping (Data?) -> Void) {
        NSLog("Current imagePath: \(self.fileURL)")
        guard let url = URL(string: "http://localhost:443/api/public/photo/download/\(self.fileURL)") else {return}
        var request = URLRequest(url: url)
        request.setValue("application/octet-stream", forHTTPHeaderField: "Content-Type")
        request.httpMethod = "GET"
        
        let dataTask = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let data = data {
                completion(data)
            }
        }
        dataTask.resume()
    }
}
