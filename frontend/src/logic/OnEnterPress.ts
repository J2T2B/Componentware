export default function OnEnterPress(then: () => void): (event: React.KeyboardEvent<HTMLInputElement>) => void {
    return (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key === "Enter") {
            then();
        }
    }
}