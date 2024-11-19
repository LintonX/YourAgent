import React, { createContext, useEffect, useState } from "react";
import "../../App.css";
import "../SelectionAgentView/selectionAgentView.css";
import NavBar from "../../components/NavBar/NavBar";
import MapStateSelector from "../../components/MapSelector/MapStateSelector";
import { AnimatePresence } from "framer-motion";
import ClientSwipeAnimation from "../../components/ClientSearchDetailCarousel/ClientSwipeAnimation";
import CountySelector from "../../components/CountySelector/CountySelector";
import {
  AgentModel,
  AgentTier,
  BASIC_PRICE,
  COUNTY_QUANTITY_MAP,
  PREMIUM_PRICE,
  PRICE_TIER_MAP,
  SERVER,
  STANDARD_PRICE,
  STATE_ABBREVIATIONS,
} from "../../constants";
import PricingCard from "../../components/PricingCard/PricingCard";
import PrimaryButton from "../../components/PrimaryButton/PrimaryButton";
import { MoonLoader } from "react-spinners";

interface SelectionAgentViewProps {
  agentContext: AgentModel;
}

type SelectionContextType = {
  state: string;
  setState: React.Dispatch<React.SetStateAction<string>>;
  selectedCounties: string[];
  setSelectedCounties: React.Dispatch<React.SetStateAction<string[]>>;
  countyQuantity: number;
  setCountyQuantity: React.Dispatch<React.SetStateAction<number>>;
};

export const SelectionContext = createContext<SelectionContextType>({
  state: "",
  setState: () => {},
  selectedCounties: [],
  setSelectedCounties: () => {},
  countyQuantity: 0,
  setCountyQuantity: () => {},
});

function SelectionAgentView({ agentContext }: SelectionAgentViewProps) {
  const [level, setLevel] = useState("");
  const [state, setState] = useState("");
  const [selectedCounties, setSelectedCounties] = useState<string[]>([]);
  const [countyQuantity, setCountyQuantity] = useState<number>(-1);
  const [currentComponent, setCurrentComponent] = useState<number>(0);

  const handlePriceSelection = (level: string) => {
    setLevel(PRICE_TIER_MAP[level as keyof typeof PRICE_TIER_MAP]);
    setCountyQuantity(
      COUNTY_QUANTITY_MAP[PRICE_TIER_MAP[level as keyof typeof PRICE_TIER_MAP]]
    );
    setCurrentComponent(1);
  };

  useEffect(() => {
    if (state) {
      setCurrentComponent(2);
    }
  }, [state]);

  useEffect(() => {
    console.log("county quantity ", countyQuantity);
    if (countyQuantity === 0) {
      setCurrentComponent(3);
    }
  }, [countyQuantity]);

  const createCheckoutSession = async (agentContext: AgentModel) => {

    agentContext.tier = level.toUpperCase() as AgentTier;
    agentContext.selectedState = STATE_ABBREVIATIONS[state as keyof typeof STATE_ABBREVIATIONS] || state;
    agentContext.selectedCounties = selectedCounties;

    const response = await fetch(`${SERVER}auth/createStripeSession`, {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(agentContext)
    })

    if (response.status === 200) {
      const checkoutSessionUrl = await response.text();
      console.log(checkoutSessionUrl);
      window.open(checkoutSessionUrl);
    } else {
      //redirect to error page
    }
  }

  useEffect(() => {
    if (currentComponent === 3) {
      createCheckoutSession(agentContext);
    }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [currentComponent]);

  return (
    <SelectionContext.Provider
      value={{
        state,
        setState,
        selectedCounties,
        setSelectedCounties,
        countyQuantity,
        setCountyQuantity,
      }}
    >
      <>
        <NavBar navBarItems={[]} />
        <div className="main-container">
          <AnimatePresence mode="wait" initial={true}>
            {currentComponent === 0 && (
              <ClientSwipeAnimation key="price">
                <div className="pricing-layout-container">
                  <h1 className="pricing-header">Select a plan to proceed.</h1>
                  <h5 className="pricing-header-sub-header">
                    Plans starting at ${BASIC_PRICE}
                  </h5>
                  <div className="pricing-cards">
                    <PricingCard
                      price={BASIC_PRICE}
                      title="Basic"
                      briefDescription="For agents focused on steady, consistent real estate opportunities in their local market."
                      description={
                        <>
                          Choose up to <strong>2</strong> counties within your
                          chosen state
                        </>
                      }
                      handleOnClick={() => handlePriceSelection(BASIC_PRICE)}
                      button={
                        <PrimaryButton
                          text="Get Started"
                          className="btn primary"
                          onClick={() => {}}
                        />
                      }
                    />
                    <PricingCard
                      price={STANDARD_PRICE}
                      title="Standard"
                      briefDescription="For agents handling a higher volume of transactions and looking to expand."
                      description={
                        <>
                          Choose up to <strong>3</strong> counties within your
                          chosen state
                        </>
                      }
                      handleOnClick={() => handlePriceSelection(STANDARD_PRICE)}
                      button={
                        <PrimaryButton
                          text="Get Started"
                          className="btn primary"
                          onClick={() => {}}
                        />
                      }
                    />
                    <PricingCard
                      price={PREMIUM_PRICE}
                      optionalPrice={"68"}
                      title="Premium"
                      briefDescription="For agents or teams managing larger volumes."
                      description={
                        <>
                          Choose up to <strong>7</strong> counties within your
                          chosen state
                        </>
                      }
                      handleOnClick={() => handlePriceSelection(PREMIUM_PRICE)}
                      button={
                        <PrimaryButton
                          text="Get Started"
                          className="btn primary"
                          onClick={() => {}}
                        />
                      }
                    />
                  </div>
                </div>
              </ClientSwipeAnimation>
            )}
            {currentComponent === 1 && (
              <ClientSwipeAnimation key="state">
                <div className="selection-header-state">
                  Select your&nbsp;
                  <span style={{ color: "var(--quaternary-color)" }}>
                    state
                  </span>
                  .
                </div>
                <div className="map-state-container">
                  <div className="map-state-box">
                    <MapStateSelector />
                  </div>
                </div>
              </ClientSwipeAnimation>
            )}
            {currentComponent === 2 && (
              <ClientSwipeAnimation key="county">
                <div className="selection-header-county">
                  Select&nbsp;
                  <span style={{ color: "var(--quaternary-color)" }}>
                    {countyQuantity}
                  </span>
                  &nbsp;more&nbsp;{countyQuantity > 1 ? "counties" : "county"}
                  &nbsp;in {state}.
                </div>
                <div className="map-county-container">
                  <div className="map-county-box">
                    <CountySelector tier={level!} />
                  </div>
                </div>
              </ClientSwipeAnimation>
            )}
            {currentComponent === 3 && (
              <div className="loading-container">
                <MoonLoader />
              </div>
            )}
          </AnimatePresence>
        </div>
      </>
    </SelectionContext.Provider>
  );
}

export default SelectionAgentView;
