export interface IGameOverListener {
    onGameOver(): void;
}

export function isGameOverListener(obj: any): obj is IGameOverListener {
    const gol = obj as IGameOverListener;
    return gol.onGameOver !== undefined;
}