import {ContentChild, Directive, ElementRef, HostListener} from "@angular/core";

@Directive({
    selector: '[dropImage]',
    standalone: true
})
export class DropImageDirective {
    @ContentChild('imageInput') imageInput!: ElementRef;
    @ContentChild('labelParagraph') labelParagraph!: ElementRef;
    @ContentChild('icon') icon!: ElementRef;

    constructor(private readonly elementRef: ElementRef) {
    }

    @HostListener('dragover', ['$event'])
    onDragOver(event: DragEvent) {
        event.preventDefault();
    }

    @HostListener('drop', ['$event'])
    onDrop(dragEvent: DragEvent) {
        dragEvent.preventDefault();
        this.setSelectedFile(dragEvent)
        this.setSelectedStyles();
    }

    @HostListener('change', ['$event'])
    onSelect() {
        this.setSelectedStyles()
    }

    private setSelectedFile(dragEvent: DragEvent) {
        const imgElement = this.imageInput.nativeElement;
        const files = (dragEvent.dataTransfer?.files as FileList);
        console.log(files)
        imgElement.files = files;
        imgElement.dispatchEvent(new Event('change'));
    }

    private setSelectedStyles(): void {
        const pElement = this.labelParagraph.nativeElement;
        const color = '#168AAD';
        pElement.innerText = this.imageInput.nativeElement.files[0].name;
        pElement.style.color = color;
        this.icon.nativeElement.style.color = color;
        this.elementRef.nativeElement.style.borderColor = color;
    }
}
