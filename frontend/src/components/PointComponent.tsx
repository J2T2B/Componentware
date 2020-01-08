import React from "react";
import { Progress } from "reactstrap";

export interface PointProps {
    label: string;
    score: number;
}

export function PointComponent({label, score}: PointProps) : JSX.Element {
    var color = "primary"; // 16 - 50

    if (score <= 5) { // 0 - 5
        color = "danger";
    }
    else if (score <= 15) { // 6 - 15
        color = "warning";
    }
    else if (score >= 70) { // 70 - 100
        color = "success";
    }
    else if (score >= 50) { // 50 - 70
        color = "info";
    }

    return <div>
        {label}
        <Progress color={color} value={score} />
    </div>;
}