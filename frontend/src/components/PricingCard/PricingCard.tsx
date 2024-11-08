import React from "react";
import "../../App.css";
import "./pricingCard.css";
import { useNavigate } from "react-router";
import { PRICE_TIER_MAP } from "../../constants";
import PrimaryButton from "../PrimaryButton/PrimaryButton";

type PricingCardProps = {
  price: string;
  optionalPrice?: string;
  title: string;
  briefDescription: string;
  description: React.ReactNode;
};

function PricingCard({
  price,
  optionalPrice,
  title,
  briefDescription,
  description,
}: PricingCardProps) {
  const navigate = useNavigate();

  const navigateTo = (route: string) => {
    navigate(route);
  };

  const handleOnClick = (price: string) => {
    console.log(price);
    if (price in PRICE_TIER_MAP) {
      navigateTo(
        `/tier/${PRICE_TIER_MAP[price as keyof typeof PRICE_TIER_MAP]}`
      );
    }
  };

  return (
    <div className="pricing-card" onClick={() => handleOnClick(price)}>
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
        <PrimaryButton
          text="Get Started"
          className="btn primary"
          onClick={() => handleOnClick(price)}
        />
      </div>
    </div>
  );
}

export default PricingCard;
