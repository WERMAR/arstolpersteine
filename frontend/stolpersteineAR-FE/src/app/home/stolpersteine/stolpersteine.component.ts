import {Component, OnInit} from '@angular/core';
import {SecuredStolpersteineService, StolpersteineResponseDto} from "../../gen/secured-api";
import {PublicService} from "../../gen/public-api";
import {MarkerMapsModel} from "../../shared/model/marker-maps.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-stolpersteine',
  templateUrl: './stolpersteine.component.html',
  styleUrls: ['./stolpersteine.component.scss']
})
export class StolpersteineComponent implements OnInit {

  constructor(private readonly stolpersteinService: SecuredStolpersteineService,
              private readonly router: Router) {
  }

  markers: MarkerMapsModel[] = []
  zoom = 12;
  center: google.maps.LatLngLiteral = {
    lat: 49.786774,
    lng: 9.953637
  }
  options: google.maps.MapOptions = {
    zoomControl: true,
    scrollwheel: true,
    disableDoubleClickZoom: true,
    maxZoom: 20,
    minZoom: 4,
  };

  selectedStolperstein: StolpersteineResponseDto | undefined;

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

  onSelectMarker(id: number) {
    console.log("Selected ID: " + id);
    this.stolpersteinService.getStolpersteinForId(id).subscribe(response => {
      console.log(response);
      this.selectedStolperstein = response;
    });
  }

  onRedirectToManage() {
    this.router.navigateByUrl('/home/stolpersteine/manage', {state: {stolpersteinId: this.selectedStolperstein?.id}}).then()
  }
}
