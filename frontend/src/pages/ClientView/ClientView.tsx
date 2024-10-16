import React from "react";
import "../../App.css";
import "../ClientView/clientView.css";
import NavBar from "../../components/NavBar/NavBar";
import {
  CLIENT_VIEW_FEATURES_CONTENT,
  CLIENT_VIEW_NAVBAR_ITEMS
} from "../../constants";
import ClientFeatureCard from "../../components/ClientFeatureCard/ClientFeatureCard";
import ClientSearchDetailCarousel from "../../components/ClientSearchDetailCarousel/ClientSearchDetailCarousel";

function ClientView() {
  return (
    <>
      <NavBar navBarItems={CLIENT_VIEW_NAVBAR_ITEMS} />
      <div className="client-view-layout">
        <div className="row client-top-container" id="row client-top-container">
          <div className="dynamic-carousel-container">
            <ClientSearchDetailCarousel />
          </div>
        </div>
        <div
          className="row client-bottom-container"
          id="row client-bottom-container"
        >
          <div className="client-features-container">
            <h1 className="client-features-header">How It Works</h1>
            <div style={{ display: "flex", justifyContent: "space-evenly" }}>
              {CLIENT_VIEW_FEATURES_CONTENT.map((item, index) => (
                <ClientFeatureCard
                  key={index}
                  step={item.step}
                  title={item.title}
                  description={item.description} />
              ))}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default ClientView