import { Component } from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
})
export class ModalComponent {
  showModal = false;
  toggleModal(){
    this.showModal = !this.showModal;
  }
}
