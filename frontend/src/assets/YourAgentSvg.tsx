import React from "react";
import { useNavigate } from "react-router";

function YourAgentSvg() {

    const navigate = useNavigate();

  return (
    <svg
      className="youragent-svg-logo"
      viewBox="27.3752 196.766 438.9857 79"
      xmlns="http://www.w3.org/2000/svg"
      onClick={() => navigate('/')}
      style={{cursor:"pointer"}}
    >
      <text
        style={{
          fontFamily: "Futura",
          fontSize: "60.8px",
          fontWeight: 500,
          letterSpacing: "2.6px",
          whiteSpace: "pre",
        }}
        x="153.15"
        y="259.766"
        transform="matrix(0.9999999999999999, 0, 0, 0.9999999999999999, -1.4210854715202004e-14, -2.842170943040401e-14)"
      >
        YourAgent
      </text>
      <rect
        x="233.627"
        y="399.464"
        width="67.333"
        height="5.883"
        style={{
          paintOrder: "fill",
          transformOrigin: "267.294px 402.405px",
        }}
        transform="matrix(0.857167124748, -0.515037894249, 0.515037894249, 0.857167124748, -209.545519604875, -164.498796806833)"
      />
      <rect
        x="56.566"
        y="576.426"
        width="14.015"
        height="7.045"
        style={{
          paintOrder: "fill",
          transformOrigin: "63.5735px 579.949px",
        }}
        transform="matrix(0, 1, -1, 0, -17.710159301776, -340.918334960939)"
      />
      <rect
        x="233.627"
        y="399.464"
        width="67.333"
        height="5.883"
        style={{
          paintOrder: "fill",
          transformOrigin: "267.293px 402.405px",
        }}
        transform="matrix(0.857167124748, 0.515037894249, -0.515037894249, 0.857167124748, -154.130570942784, -164.513291937524)"
      />
    </svg>
  );
}

export default YourAgentSvg;
