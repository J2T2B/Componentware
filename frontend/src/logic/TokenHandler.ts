import moment from "moment";

interface SavedAccessToken {
    expires: string | moment.Moment,
    token: string
}

interface AccessToken extends SavedAccessToken {
    expires: moment.Moment;
}

export default class TokenHandler {

    private static readonly accessTokenKey = "access_token";

    get hasLogin(): boolean {
        const accessToken = this.accessToken;
        // Ist ein Token da und ist dieser noch gültig
        return accessToken !== null && accessToken.expires.isBefore(moment());
    }

    get token(): string {
        return this.accessToken!.token;
    }

    private get accessToken(): AccessToken | null {
        const storage = localStorage.getItem(TokenHandler.accessTokenKey);
        if (storage !== null) {
            const parsed = JSON.parse(storage) as SavedAccessToken;
            parsed.expires = moment(parsed.expires);
            return parsed as AccessToken;
        }

        return null
    }

}