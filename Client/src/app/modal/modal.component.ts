import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
})
export class ModalComponent {
  @Input() title: string = "title";
  showModal = false;

  toggleModal(){
    this.showModal = !this.showModal;
  }

}
