import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {
  PhotoReqDto,
  PhotoUploadDto, PhotoUploadResponseDto, SecuredPhotosService,
  SecuredStolpersteineService, StolpersteineReqDto,
  StolpersteineResponseDto
} from "../../../gen/secured-api";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ThemePalette} from "@angular/material/core";
import {MatChipListboxChange} from "@angular/material/chips";
import {DatePipe} from "@angular/common";
import {ToastrService} from "ngx-toastr";

export interface PhotoFile {
  file: File;
  heading: number;
}

@Component({
  selector: 'app-stolperstein-manage-view',
  templateUrl: './stolperstein-manage-view.component.html',
  styleUrl: './stolperstein-manage-view.component.scss'
})
export class StolpersteinManageViewComponent {
  stolpersteinId: number;
  stolperstein: StolpersteineResponseDto | undefined;
  stolpersteinForm: FormGroup;
  fileHeadingValidity: boolean = true;

  photos: PhotoFile[] = [];

  constructor(private readonly router: Router,
              private readonly stolpersteinService: SecuredStolpersteineService,
              private readonly photoUploadService: SecuredPhotosService,
              private readonly datePipe: DatePipe,
              private readonly toastService: ToastrService) {
    const state = this.router.getCurrentNavigation()?.extras.state
    this.stolpersteinId = state ? state['stolpersteinId'] : -1;
    if (this.stolpersteinId > -1) {
      this.stolpersteinService.getStolpersteinForId(this.stolpersteinId).subscribe(response => {
          this.stolperstein = response;
          console.log("Received Stolperstein: ", this.stolperstein);
          this.stolpersteinForm.patchValue({
            firstname: this.stolperstein.victim?.firstname,
            lastname: this.stolperstein.victim?.lastname,
            dateOfBirth: this.stolperstein.victim?.dateOfBirth,
            dateOfDeath: this.stolperstein.victim?.dateOfDeath,
            streetName: this.stolperstein.address?.streetName,
            city: this.stolperstein.address?.city,
            postcode: this.stolperstein.address?.postCode,
            houseNumber: this.stolperstein.address?.houseNumber,
            description: this.stolperstein.description
          });
        },
        error => {
          console.error(error)
          this.toastService.error('Ein Fehler ist aufgetreten, probieren Sie es erneut');
        });
    } else {
      this.toastService.warning('Keine Stolperstein-ID vorhanden');
      this.router.navigateByUrl('/home/stolpersteine').then()
    }

    this.stolpersteinForm = new FormGroup({
      firstname: new FormControl({value: this.stolperstein?.victim?.firstname, disabled: false}, {
        updateOn: 'change',
        validators: [Validators.required]
      }),
      lastname: new FormControl({value: this.stolperstein?.victim?.lastname, disabled: false}, {
        updateOn: 'change',
        validators: [Validators.required]
      }),
      dateOfBirth: new FormControl({value: this.stolperstein?.victim?.dateOfBirth, disabled: false}, {
        updateOn: 'change',
        validators: [Validators.required]
      }),
      dateOfDeath: new FormControl({value: this.stolperstein?.victim?.dateOfDeath, disabled: false}, {
        updateOn: 'change',
        validators: [Validators.required]
      }),
      streetName: new FormControl({value: this.stolperstein?.address?.streetName, disabled: false}, {
        updateOn: 'change',
        validators: [Validators.required]
      }),
      postcode: new FormControl({value: this.stolperstein?.address?.postCode, disabled: false}, {
        updateOn: 'change',
        validators: [Validators.required]
      }),
      city: new FormControl({value: this.stolperstein?.address?.city, disabled: false}, {
        updateOn: 'change',
        validators: [Validators.required]
      }),
      houseNumber: new FormControl({value: this.stolperstein?.address?.houseNumber, disabled: false}, {
        updateOn: 'change',
        validators: [Validators.required]
      }),
      description: new FormControl({value: this.stolperstein?.description, disabled: false}, {
        updateOn: 'change',
        validators: [Validators.required]
      }),
    });

  }

  onReturnToMainPage() {
    this.router.navigateByUrl('/home/stolpersteine').then();
  }

  onUploadPhoto($event: any) {
    if ($event.target.files[0]) {
      const photo = {
        file: $event.target.files[0],
        heading: 0
      } as PhotoFile
      this.photos.push(photo);
      this.fileHeadingValidity = false;
    }
  }

  onSelectElement($event: MatChipListboxChange, photo: File) {
    if ($event.value) {
      const photoFile = this.photos.find(e => e.file.name === photo.name)
      if (photoFile) {
        photoFile.heading = this.getHeadingOfChip($event.value)
        this.fileHeadingValidity = true;
      }
    } else {
      this.fileHeadingValidity = false;
    }
  }

  private getHeadingOfChip(direction: string): number {
    switch (direction.toUpperCase()) {
      case 'NORDEN':
        return 0;
      case 'OST':
        return 90;
      case 'SÜDEN':
        return 180;
      case 'WEST':
        return 270;
    }
    return -1;
  }

  onUpload() {
    const photoUploadDto = this.photos.map(e => e.file);
    if (photoUploadDto.length > 0) {

      this.photoUploadService.uploadPhotos(photoUploadDto).subscribe(response => {
        const stolpersteinRequest = this.getStolpersteinRequestDto(response);
        console.log(stolpersteinRequest);
        this.stolpersteinService.updateStolpersteine(this.stolpersteinId, stolpersteinRequest).subscribe(response => {
            this.toastService.info(`Stolperstein aktualisiert mit ID: ${this.stolpersteinId}`);
            this.router.navigateByUrl('/home/stolpersteine').then();
          },
          error => {
            console.error(error)
            this.toastService.error('Ein Fehler ist aufgetreten, probieren Sie es erneut');
          })
      }, error => {
        console.error(error)
        this.toastService.error('Ein Fehler ist aufgetreten, probieren Sie es erneut');
      });
    } else {
      const stolpersteinRequest = this.getStolpersteinRequestDto(undefined);
      console.log(stolpersteinRequest);
      this.stolpersteinService.updateStolpersteine(this.stolpersteinId, stolpersteinRequest).subscribe(response => {
          if (response) {
            this.toastService.info(`Stolperstein aktualisiert mit ID: ${this.stolpersteinId}`);
            this.router.navigateByUrl('/home/stolpersteine').then();
          }
        },
        error => {
          console.error(error)
          this.toastService.error('Ein Fehler ist aufgetreten, probieren Sie es erneut');
        })
    }
  }

  private getStolpersteinRequestDto(photoResponse: PhotoUploadResponseDto[] | undefined): StolpersteineReqDto {
    return {
      description: this.stolpersteinForm.controls['description'].value,
      address: {
        city: this.stolpersteinForm.controls['city'].value,
        streetName: this.stolpersteinForm.controls['streetName'].value,
        houseNumber: this.stolpersteinForm.controls['houseNumber'].value,
        postCode: this.stolpersteinForm.controls['postcode'].value,
      },
      position: {
        lat: this.stolperstein?.position?.lat!,
        lng: this.stolperstein?.position?.lng!
      },
      victim: {
        firstname: this.stolpersteinForm.controls['firstname'].value,
        lastname: this.stolpersteinForm.controls['lastname'].value,
        dateOfBirth: this.datePipe.transform(this.stolpersteinForm.controls['dateOfBirth'].value, 'yyyy-MM-dd')!,
        dateOfDeath: this.datePipe.transform(this.stolpersteinForm.controls['dateOfDeath'].value, 'yyyy-MM-dd')!,
      },
      photos: photoResponse?.map(e => ({
        id: e.id,
        heading: this.photos.find(p => e.downloadURI!.includes(p.file.name))?.heading
      } as PhotoReqDto))
    }
  }
}
