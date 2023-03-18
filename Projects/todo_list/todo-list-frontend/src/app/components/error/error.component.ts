import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  ElementRef,
  Input,
  ViewChild,
  ViewContainerRef
} from '@angular/core';
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-error',
  template: `
    <div #errorContainer *ngIf="errorMessages && errorMessages.length" class="error-box">
      <span (click)="close()" class="close">x</span>
      <p *ngFor="let errorMessage of errorMessages">{{errorMessage}}</p>
    </div>
  `,
  styles: [`
    .error-box {
      width: 25em;
      word-wrap: break-word;
      background-color: #FF595E;
      padding: .8em;
      border-radius: .3em;
      color: white;
      margin: 0 auto;
      p:not(:last-of-type) {
        margin-bottom: .7em;
      }
      p {
        text-align: center;
      }
      
      .close {
        float: right;
        color: #184E77;
        position: absolute;
        top: .1em;
        right: .3em;
        font-size: 1.5em;
        &:hover {
          color: white;
          cursor: pointer;
        }
      }
    }
  `],
  standalone: true,
  imports: [CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ErrorComponent {
  @Input() errorMessages: string[] = [];
  @ViewChild('errorContainer') errorContainer!: ElementRef;

  public close() {
    this.errorContainer.nativeElement.style.display = 'none';
  }
}
