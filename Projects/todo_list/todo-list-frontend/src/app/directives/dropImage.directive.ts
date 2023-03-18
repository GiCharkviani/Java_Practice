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
        const files = (dragEvent.dataTransfer?.files as FileList);
        const file = files.item(0);

        if(files.length > 1) {
            this.displayError("Only one file is allowed");
            return;
        }
        if(file && !file.type.startsWith('image/')) {
            this.displayError("Only images are allowed");
            return;
        }

        this.setSelectedFile(files)
        this.setSelectedStyles();
    }

    @HostListener('change', ['$event'])
    onSelect() {
        this.setSelectedStyles()
    }

    private setSelectedFile(fileList: FileList) {
        const imgElement = this.imageInput.nativeElement;
        imgElement.files = fileList;
        imgElement.dispatchEvent(new Event('change'));
    }

    private setSelectedStyles(): void {
        const files = this.imageInput.nativeElement.files;
        const pElement = this.labelParagraph.nativeElement;
        const successColor = '#52B69A';
        if(files.length === 1) {
            pElement.innerText = files[0].name;
            pElement.style.color = successColor;
            this.icon.nativeElement.style.color = successColor;
            this.elementRef.nativeElement.style.borderColor = successColor;
        }
    }

    private displayError(errorMsg: string) {
        const pElement = this.labelParagraph.nativeElement;
        const errorColor = '#FF595E';
        pElement.innerText = errorMsg;
        pElement.style.color = 'red';
        this.icon.nativeElement.style.color = errorColor;
        this.elementRef.nativeElement.style.borderColor = errorColor;
        setTimeout(() => this.setSelectedStyles(), 1500)
    }
}
