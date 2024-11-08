import React from "react";
import "../../App.css";
import "../Footer/footer.css";
import YourAgentSvg from "../../assets/YourAgentSvg";
import PrimaryButton from "../PrimaryButton/PrimaryButton";
import { NavHashLink } from "react-router-hash-link";

function Footer() {
  return (
    <div className="footer-container">
      <div className="footer-left-container">
       <YourAgentSvg/>
       <p style={{color:"white", marginTop:"20px", marginBottom:"30px", fontWeight:"400"}}>
        Building relationships with buyers and renters in your area.
       </p>
       <PrimaryButton className="btn primary" text="Send us a message" onClick={() => console.log("hi")} />
      </div>
      <div className="footer-right-container">
        <div className="footer-content-column">
            <h3>For Clients</h3>
            <ul>
                <li><NavHashLink className={"footer-link"} to="/#row client-top-container">Search<span style={{color:"var(--primary-color)"}}>&nbsp;&gt;</span></NavHashLink></li>
                <div style={{margin:"10px"}}></div>
                <li><NavHashLink className={"footer-link"} to="/#row client-bottom-container">How It Works<span style={{color:"var(--primary-color)"}}>&nbsp;&gt;</span></NavHashLink></li>
            </ul>
        </div>
        <div className="footer-content-column">
            <h3>For Agents</h3>
            <ul>
                <li><NavHashLink className={"footer-link"} to="/Agents#row pricing-middle-container">How It Works<span style={{color:"var(--primary-color)"}}>&nbsp;&gt;</span></NavHashLink></li>
                <div style={{margin:"10px"}}></div>
                <li><NavHashLink className={"footer-link"} to="/Agents#row pricing-bottom-container">Pricing<span style={{color:"var(--primary-color)"}}>&nbsp;&gt;</span></NavHashLink></li>
            </ul>
        </div>
      </div>
    </div>
  );
}

<span style={{color:"var(--primary-color)"}}>&gt;</span>

export default Footer;
