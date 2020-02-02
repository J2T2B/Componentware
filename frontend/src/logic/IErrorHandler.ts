import {VoidLike} from "./VoidLike";

export interface IErrorHandler {
    onError(errorMessage: string): VoidLike;
}

export function isIErrorHandler(obj: any) : obj is IErrorHandler {
    const errorHandler = obj as IErrorHandler;
    return errorHandler.onError !== undefined && typeof(errorHandler.onError) === "function";
}