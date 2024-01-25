//
//  RoutesView.swift
//  ARStolpersteine
//
//  Created by Marcel Wernisch on 17.11.23.
//

import SwiftUI
import MapKit
import OpenAPIRuntime
import OpenAPIURLSession

struct RoutesView: View, MapDelegate {
    
    @StateObject var manager = LocationManager()
    @State private var updatedRegion: MKCoordinateRegion? = nil
    @State private var routeList = [StolpersteinListItem]()
    @State private var stolpersteinMap = [Int64: StolpersteinWithDistance]()
    @State var showingBottomSheet = false
    @State var selectedBottomSheetVal: StolpersteinWithDistance? = nil
    private let client: Client
    
    
    
    var body: some View {
        NavigationView {
            ZStack(alignment: .top) {
                Color.backgroundColor.ignoresSafeArea()
                VStack(alignment: .leading){
                    Text("Stolpersteine in der Nähe")
                        .font(.title2)
                        .frame(alignment: .leading)
                        .fontWeight(.bold)
                        .foregroundColor(.primary)
                        .padding(.bottom, 20)
                    ForEach(self.stolpersteinMap.sorted { $0.key < $1.key }, id: \.key) {key, value in
                        StolpersteinRouteListItem(value).onTapGesture {
                            self.selectedBottomSheetVal = value
                            showingBottomSheet.toggle()
                        }
                    }
                }.padding(10)
            }.onAppear {
                manager.startUpdatingPosition(self)
            }
            .onDisappear {
                manager.stopUpdatingPosition()
            }.toolbar {
                ToolbarItem(placement: .topBarLeading) {
                    Image(uiImage: UIImage(named:"logo_final")!)
                        .resizable()
                        .scaledToFit()
                        .frame(width: 150, height:150)
                }
            }.sheet(item: $selectedBottomSheetVal) { item in
                ZStack(alignment: .top) {
                    VStack {
                        Text(String("\(item.stolperstein.victim!.firstname!) \(item.stolperstein.victim!.lastname!)"))
                            .font(.title3)
                            .frame(alignment: .center)
                            .fontWeight(.bold)
                            .foregroundColor(.primary)
                        VStack (alignment: .leading) {
                            Divider()
                                .padding(.bottom, 20)
                            VStack(alignment: .leading) {
                                Group {
                                    Text(String("Allgemeine Informationen"))
                                        .frame(alignment: .center)
                                        .fontWeight(.bold)
                                        .foregroundColor(.gray)
                                    Divider()
                                    HStack {
                                        Text(String("Vorname: ")).bold()
                                        Text(item.stolperstein.victim!.firstname!)
                                    }
                                    HStack {
                                        Text(String("Nachname: ")).bold()
                                        Text(item.stolperstein.victim!.lastname!)
                                    }
                                    HStack {
                                        Text(String("Geburtsdatum: ")).bold()
                                        Text(item.stolperstein.victim!.dateOfBirth ?? String("n.A."))
                                    }
                                    HStack {
                                        Text(String("Todesdatum: ")).bold()
                                        Text(item.stolperstein.victim!.dateOfDeath ?? String("n.A."))
                                    }
                                    Divider()
                                        .padding(.bottom, 30)
                                }
                                Group {
                                    Text(String("Adress Informationen"))
                                        .frame(alignment: .center)
                                        .fontWeight(.bold)
                                        .foregroundColor(.gray)
                                    Divider()
                                    HStack {
                                        Text(String("Straße & Haus-Nr: ")).bold()
                                        Text(String("\(item.stolperstein.address!.streetName ?? "n.A.") \(item.stolperstein.address!.houseNumber ?? "")"))
                                    }
                                    HStack {
                                        Text(String("PLZ & Stadt: ")).bold()
                                        Text(String("\(item.stolperstein.address!.postCode ?? "n.A.") \(item.stolperstein.address!.city ?? "")"))
                                    }
                                    HStack {
                                        Text(String("Geburtsdatum: ")).bold()
                                        Text(item.stolperstein.victim!.dateOfBirth ?? String("n.A."))
                                    }
                                    HStack {
                                        Text(String("Todesdatum: ")).bold()
                                        Text(item.stolperstein.victim!.dateOfDeath ?? String("n.A."))
                                    }
                                    Divider()
                                        .padding(.bottom, 30)
                                }
                                Text(String("Beschreibung"))
                                    .frame(alignment: .center)
                                    .fontWeight(.bold)
                                    .foregroundColor(.gray)
                                Divider()
                                    .padding(.bottom, 20)
                                Text(item.stolperstein.description ?? "")
                            }
                        }
                    }
                }.padding(.leading, 10)
                    .padding(.trailing, 10)
            }
        }
    }
    
    
    init() {
        self.client = Client(serverURL: try! Servers.server1(), transport: URLSessionTransport())
    }
    
    func regionDidChanged(_ region: MKCoordinateRegion) {
        NSLog("Region Changed")
        self.updatedRegion = region
        self.loadData()
    }
    
    func loadData() {
        if (self.updatedRegion != nil) {
            self.routeList.removeAll()
            Task {
                try await loadStolpersteine()
                loadStolpersteinForRouteList()
            }
        }
    }
    
    func loadStolpersteine() async throws {
        NSLog("Load Stolpersteine")
        let currentLocation = (long: self.updatedRegion!.center.longitude, lat: self.updatedRegion!.center.latitude)
        
        let response = try await client.getStolpersteineForUser(query: Operations.getStolpersteineForUser.Input.Query(radius: 0.002, latUser: Float(currentLocation.lat), lngUser: Float(currentLocation.long)))
        switch response {
        case let .ok(okResponse):
            switch okResponse.body {
            case .json(let json):
                self.addPinsToList(json)
            }
        case .undocumented(statusCode: let statusCode, _):
            NSLog("StatusCode: \(statusCode)")
        case .badRequest(_):
            NSLog("StatusCode: \(400)")
        case .notFound(_):
            NSLog("StatusCode: \(404)")
        case .internalServerError(_):
            NSLog("StatusCode: \(500)")
        }
    }
    
    private func addPinsToList(_ data: [Components.Schemas.StolpersteinPositionDto]) {
        for stolpersteinPosition in data {
            let location = getCLLocationOfPoint(CLLocationCoordinate2D(latitude: Double(stolpersteinPosition.position!.lat!), longitude: Double(stolpersteinPosition.position!.lng!)))
            let distance = getDistanceToCurrentLocation(location)
            let stolperstein = StolpersteinListItem(
                stolpersteinId: stolpersteinPosition.id!, distance: distance
            )
            self.routeList.append(stolperstein)
        }
    }
    
    private func getCLLocationOfPoint(_ coordinates: CLLocationCoordinate2D) -> CLLocation {
        return CLLocation(latitude: coordinates.latitude, longitude: coordinates.longitude)
    }
    
    private func getCLLocationOfCurrentLocation() -> CLLocation {
        return CLLocation(latitude: self.updatedRegion!.center.latitude, longitude: self.updatedRegion!.center.longitude)
        
    }
    
    private func getDistanceToCurrentLocation(_ location: CLLocation) -> Double {
        return location.distance(from: getCLLocationOfCurrentLocation())
    }
    
    // MARK: Load specific Stolpersteine
    private func loadStolpersteinForRouteList() {
        self.stolpersteinMap.removeAll()
        for stolpersteinItem in self.routeList {
            Task {
                try await loadStolpersteinForId(stolpersteinItem.stolpersteinId, stolpersteinItem.distance)
            }
        }
    }
    
    private func loadStolpersteinForId(_ stolpersteinId: Int64, _ distance: Double) async throws {
        let response = try await client.getStolpersteinForID(path: Operations.getStolpersteinForID.Input.Path(stolpersteinId: stolpersteinId))
        switch response {
        case let .ok(okResponse):
            switch okResponse.body {
            case .json(let json):
                print("Response arrived")
                stolpersteinMap[stolpersteinId] = StolpersteinWithDistance(stolperstein: json, distance: distance)
            }
        case .undocumented(statusCode: let statusCode, _):
            NSLog("StatusCode: \(statusCode)")
        case .badRequest(_):
            NSLog("StatusCode: \(400)")
        case .notFound(_):
            NSLog("StatusCode: \(404)")
        case .internalServerError(_):
            NSLog("StatusCode: \(500)")
            
        }
    }
}

struct StolpersteinListItem: Identifiable {
    let id = UUID()
    let stolpersteinId: Int64
    let distance: Double
}

struct StolpersteinWithDistance: Identifiable {
    let id = UUID()
    let stolperstein: Components.Schemas.StolpersteineResponseDto
    let distance: Double
}

#Preview {
    RoutesView()
}
