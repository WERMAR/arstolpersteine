import {Component, OnInit} from '@angular/core';
import {SecuredStolpersteineService} from "../../gen/secured-api";
import {PublicService} from "../../gen/public-api";
import {MarkerMapsModel} from "../../shared/model/marker-maps.model";

@Component({
  selector: 'app-stolpersteine',
  templateUrl: './stolpersteine.component.html',
  styleUrls: ['./stolpersteine.component.scss']
})
export class StolpersteineComponent implements OnInit {

  constructor(private readonly stolpersteinService: SecuredStolpersteineService, private publicService: PublicService) {
  }

  markers: MarkerMapsModel[] = []
  zoom = 12;
  center!: google.maps.LatLngLiteral;
  options: google.maps.MapOptions = {
    zoomControl: true,
    scrollwheel: true,
    disableDoubleClickZoom: true,
    maxZoom: 20,
    minZoom: 4,
  };

  ngOnInit() {
    navigator.geolocation.getCurrentPosition((position) => {
      this.center = {
        lat: position.coords.latitude,
        lng: position.coords.longitude,
      };
    });
    this.stolpersteinService.getAllStolpersteine().subscribe((val) => {
      console.log(val);
      val.map(e => {
        return {
          id: e.id,
          position: {
            lat: e.position?.lat,
            lng: e.position?.lng,
          },
          options: {animation: google.maps.Animation.BOUNCE}
        } as MarkerMapsModel
      }).forEach(e => this.markers.push(e))
    })
  }

  zoomIn() {
    if (this.zoom < this.options.maxZoom!) this.zoom++;
  }

  zoomOut() {
    if (this.zoom > this.options.minZoom!) this.zoom--;
  }

  onSelectMarker(id: number) {
    console.log("Selected ID: " + id);
  }
}
