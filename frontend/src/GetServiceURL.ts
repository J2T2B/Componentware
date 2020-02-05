let serviceUrl = "";

// "http://localhost:8080/server-1.0-SNAPSHOT"

if (process.env.NODE_ENV === "production") {
    let url = new URL(window.location.toString());
    url.hash = "";
    serviceUrl = url.toString();
    serviceUrl = serviceUrl.substring(0, serviceUrl.length - 1);
} else {
    serviceUrl = "http://localhost:8080/server-1.0-SNAPSHOT";
}

export default function GetServiceURL(): string {
    return serviceUrl;
}