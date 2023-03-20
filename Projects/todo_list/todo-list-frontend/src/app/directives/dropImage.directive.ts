import {ContentChild, Directive, ElementRef, HostListener} from "@angular/core";

@Directive({
    selector: '[dropImage]',
    standalone: true
})
export class DropImageDirective {
    private readonly DROP = 'You can drop';
    private readonly SELECT = 'Select or drop your profile picture';
    private readonly ONLY_ONE = 'Only one file is allowed';
    private readonly ONLY_IMAGE = 'Only images are allowed';
    private readonly SUCCESS_COLOR = '#168AAD';
    private readonly ERROR_COLOR = '#FF595E';
    private readonly DEFAULT_C0LOR = 'grey'

    @ContentChild('imageInput') imageInput!: ElementRef;
    @ContentChild('labelParagraph') labelParagraph!: ElementRef;
    @ContentChild('icon') icon!: ElementRef;

    constructor(private readonly elementRef: ElementRef) {
    }

    @HostListener('document:dragover', ['$event'])
    onDocumentDragOver(dragEvent: DragEvent) {
        dragEvent.preventDefault();
        this.setDragOverStyles();
    }

    @HostListener('document:drop', ['$event'])
    onDocumentDrop(dragEvent: DragEvent) {
        dragEvent.preventDefault();
        this.onDrop(dragEvent);
    }

    @HostListener('document:dragleave', ['$event'])
    onDocumentDragLeave(dragEvent: DragEvent) {
        dragEvent.preventDefault();
        this.setSelectedStyles();
    }

    @HostListener('change', ['$event'])
    onSelect() {
        this.setSelectedStyles();
    }

    private onDrop(dragEvent: DragEvent): void {
        dragEvent.preventDefault();
        const files = (dragEvent.dataTransfer?.files as FileList);
        const file = files.item(0);

        if(files.length > 1) {
            this.displayError(this.ONLY_ONE);
            return;
        }
        if(file && !file.type.startsWith('image/')) {
            this.displayError(this.ONLY_IMAGE);
            return;
        }

        this.setSelectedFile(files);
        this.setSelectedStyles();
    }

    private setSelectedFile(fileList: FileList) {
        const imgElement = this.imageInput.nativeElement;
        imgElement.files = fileList;
        imgElement.dispatchEvent(new Event('change'));
    }

    private setDragOverStyles(): void {
        this.setStyles(this.DROP,this.SUCCESS_COLOR);
    }

    private setDefaultStyles(): void {
        this.setStyles(this.SELECT,this.DEFAULT_C0LOR);
    }

    private setSelectedStyles(): void {
        const files = this.imageInput.nativeElement.files;
        files.length === 1 ? this.setStyles(files[0].name, '#168AAD') :
            this.setDefaultStyles();
    }

    private displayError(errorMsg: string) {
        this.setStyles(errorMsg, this.ERROR_COLOR);
        setTimeout(() => this.setSelectedStyles(), 1500);
    }

    private setStyles(text: string, color: string) {
        const labelElement = this.labelParagraph.nativeElement;
        labelElement.innerText = text;
        labelElement.style.color = color;
        this.elementRef.nativeElement.style.borderColor = color;
        this.icon.nativeElement.style.color = color;
    }
}
