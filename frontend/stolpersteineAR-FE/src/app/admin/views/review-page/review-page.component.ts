import { Component } from '@angular/core';

@Component({
  selector: 'app-review-page',
  templateUrl: './review-page.component.html',
  styleUrls: ['./review-page.component.scss']
})
export class ReviewPageComponent {
  displayedColumns: string[] = ['requestID', 'status', 'address', 'symbol', 'action'];
  dataSource = [
    {requestID: 10023223, status: 'REQUESTED', address: 'Musterstraße 23, Würzburg', requester: 'Max Mustermann'},
    {requestID: 10023223, status: 'REQUESTED', address: 'Musterstraße 23, Würzburg', requester: 'Max Mustermann'},
    {requestID: 10023223, status: 'REQUESTED', address: 'Musterstraße 23, Würzburg', requester: 'Max Mustermann'},
    {requestID: 10023223, status: 'REQUESTED', address: 'Musterstraße 23, Würzburg', requester: 'Max Mustermann'},
    {requestID: 10023223, status: 'REQUESTED', address: 'Musterstraße 23, Würzburg', requester: 'Max Mustermann'}
  ];
  iAmAdmin: boolean = true;

  onClickEdit() {
    alert('Edit clicked');
  }

  onMouseOver() {
    alert('Mouse Over delete');
  }
}
