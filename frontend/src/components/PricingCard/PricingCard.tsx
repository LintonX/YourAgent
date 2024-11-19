import React from "react";
import "../../App.css";
import "./pricingCard.css";

type PricingCardProps = {
  price: string;
  optionalPrice?: string;
  title: string;
  briefDescription: string;
  description: React.ReactNode;
  button: React.ReactNode;
  handleOnClick: () => void
};

function PricingCard({
  price,
  optionalPrice,
  title,
  briefDescription,
  description,
  button,
  handleOnClick
}: PricingCardProps) {
  return (
    <div className="pricing-card" onClick={handleOnClick}>
      <div className="pricing-card-top">
        <p className="card-header">{title}</p>
      </div>
      <div className="brief-description-container">
        <p className="brief-description">{briefDescription}</p>
      </div>
      <div className="pricing-card-middle">
        <p>
          ${price}{" "}
          <span className="per-month">/mo&nbsp;&nbsp;&nbsp;&nbsp;</span>
          {optionalPrice && (
            <>
              <span
                style={{
                  color: "rgba(255, 0, 0, 0.699)",
                  textDecoration: "line-through",
                  fontSize: "1.9rem",
                }}
              >
                <span style={{ color: "black" }}>${optionalPrice}</span>
              </span>
              <span className="per-month">/mo</span>
            </>
          )}
        </p>
      </div>
      <hr className="hr-line" />
      <div className="pricing-card-bottom">{description}</div>
      <div className="button-container">
        {button}
      </div>
    </div>
  );
}

export default PricingCard;
