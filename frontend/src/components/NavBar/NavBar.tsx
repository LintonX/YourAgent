import React from "react";
import "../../App.css";
import "../NavBar/navBar.css";
import { YOUR_AGENT_SVG, NavBarListItem } from "../../constants";
import PrimaryButton from "../PrimaryButton/PrimaryButton";
import { useNavigate } from "react-router-dom";

function NavBar({ navBarItems }: { navBarItems: NavBarListItem[] }) {
    
  const scrollToSection = (sectionName: string) => {
    const section = document.getElementById(sectionName);
    console.log(sectionName)
    console.log(section);
    if (section) {
      section.scrollIntoView({ behavior: "smooth" });
    }
  };

  const navigate = useNavigate();

  const navigateTo = (route: string) => {
    navigate(`/${route}`);
  };

  return (
    <div className="nav-bar-container">
      <div className="nav-bar">
        <div className="nav-bar-left">
          <img
            className="your-agent-logo"
            src={YOUR_AGENT_SVG}
            alt="YourAgent logo"
            onClick={() => {
              document.documentElement.scrollTop = 0;
            }}
          />
          <ul>
            {navBarItems.map((item, index) => (
              <li
                key={index}
                onClick={() => {
                  item.shouldNavigateTo
                    ? navigateTo(item.shouldNavigateTo)
                    : scrollToSection(item.sectionName ?? '');
                }}
              >
                {item.label}
              </li>
            ))}
          </ul>
        </div>
        <div className="nav-bar-right">
          <PrimaryButton
            text="Sign in"
            className="btn primary"
            onClick={() => {
              console.log("clicked");
            }}
          />
        </div>
      </div>
    </div>
  );
}

export default NavBar;
