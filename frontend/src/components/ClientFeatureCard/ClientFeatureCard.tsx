import React from "react";
import "../../App.css";
import "../ClientFeatureCard/clientFeatureCard.css";
import { ClientFeatureCardProps } from "../../constants";

function ClientFeatureCard({ step, title, description,}: ClientFeatureCardProps) {
  return (
    <div className="client-feature-card">
      <h2 className="client-feature-card-header">
        <span style={{ color: 'black' }}>{step}.<br></br></span>{title}
      </h2>
      <p className="client-feature-card-description">{description}</p>
    </div>
  );
}

export default ClientFeatureCard;
