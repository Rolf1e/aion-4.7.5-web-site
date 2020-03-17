export class News {

  private title;
  private image;
  private description;
  private text;

  constructor(title, image, description, text) {
    this.title = title;
    this.image = image;
    this.description = description;
    this.text = text;
  }


  get getTitle() {
    return this.title;
  }

  get getImage() {
    return this.image;
  }

  get getDescription() {
    return this.description;
  }

  get getText() {
    return this.text;
  }
}
