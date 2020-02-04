export default function setWindowTitle(title?: string) : void {
    const titleNode = document.querySelector("title");

    if (title === undefined) {
        title = "LWB William Walker";
    }
    else {
        title += " | LWB William Walker";
    }

    titleNode!.innerText = title;
}