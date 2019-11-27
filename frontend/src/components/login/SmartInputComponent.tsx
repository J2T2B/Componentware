import React from "react";
import { FormGroup, Label, Input, FormFeedback } from "reactstrap";
import { InputType } from "reactstrap/lib/Input";

type OnValueCallback<T> = (nValue: Partial<T>) => any;

export interface SmartInputProps<T> {
    label: string
    name: keyof T
    placeholder?: string
    value: string
    onValue: OnValueCallback<T>
    type: InputType
    error?: string
    autoComplete: string
}

function getValueHandler<T>(callback: OnValueCallback<T>): (event: React.ChangeEvent<HTMLInputElement>) => void {
    return (evt) => {
        let target = evt.target;
        let field = target.name as keyof T;
        let partial: Partial<T> = {};
        (partial[field] as any) = target.value;
        callback(partial);
    };
}

export function SmartInputComponent<T>(props: SmartInputProps<T>) {
    return <FormGroup>
        <Label>{props.label}</Label>
        <Input
            type={props.type}
            placeholder={props.placeholder}
            value={props.value}
            name={props.name as string}
            onChange={getValueHandler(props.onValue)}
            invalid={props.error !== undefined}
            autoComplete={props.autoComplete}
        />
        {props.error !== undefined &&
            <FormFeedback>
                {props.error}
            </FormFeedback>}
    </FormGroup>
}