export default function setWindowTitle(title?: string) : void {
    const titleNode = document.querySelector("title");

    if (title === undefined) {
        title = "Telekilo";
    }
    else {
        title += " | Telekilo";
    }

    titleNode!.innerText = title;
}