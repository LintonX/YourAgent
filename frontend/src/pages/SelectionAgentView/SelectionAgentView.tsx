import React, { createContext, useEffect, useState } from "react";
import "../../App.css";
import "../SelectionAgentView/selectionAgentView.css";
import { useParams } from "react-router-dom";
import NavBar from "../../components/NavBar/NavBar";
import MapStateSelector from "../../components/MapSelector/MapStateSelector";
import { AnimatePresence } from "framer-motion";
import ClientSwipeAnimation from "../../components/ClientSearchDetailCarousel/ClientSwipeAnimation";
import CountySelector from "../../components/CountySelector/CountySelector";
import { COUNTY_QUANTITY_MAP } from "../../constants";
import Footer from "../../components/Footer/Footer";

type SelectionContextType = {
  state: string;
  setState: React.Dispatch<React.SetStateAction<string>>;
  selectedCounties: string[];
  setSelectedCounties: React.Dispatch<React.SetStateAction<string[]>>;
  countyQuantity: number,
  setCountyQuantity: React.Dispatch<React.SetStateAction<number>>;
};

export const SelectionContext = createContext<SelectionContextType>({
  state: "",
  setState: () => {},
  selectedCounties: [],
  setSelectedCounties: () => {},
  countyQuantity: 0,
  setCountyQuantity: () => {}
});

function SelectionAgentView() {

  const { level } = useParams<string>();
  const [state, setState] = useState("");
  const [selectedCounties, setSelectedCounties] = useState<string[]>([]);
  const [currentComponent, setCurrentComponent] = useState<number>(0);
  const [countyQuantity, setCountyQuantity] = useState<number>(level ? COUNTY_QUANTITY_MAP[level] ?? 0 : 0);
  
  useEffect(() => {
    if (state) {
        setCurrentComponent(1)
    }
  }, [state])

  return (
    
    <SelectionContext.Provider
      value={{ state, setState, selectedCounties, setSelectedCounties, countyQuantity, setCountyQuantity }}
    >
      <>
        <NavBar navBarItems={[]} />
        <div className="main-container">
          <AnimatePresence mode="wait" initial={true}>
            {currentComponent === 0 && (
              <ClientSwipeAnimation key="state">
                <div className="selection-header-state">
                  Select your&nbsp;
                  <span style={{ color: "var(--primary-color)" }}>state</span>.
                </div>
                <div className="map-state-container">
                  <div className="map-state-box">
                    <MapStateSelector />
                  </div>
                </div>
              </ClientSwipeAnimation>
            )}
            {currentComponent === 1 && (
              <ClientSwipeAnimation key="county">
                <div className="selection-header-county">
                  Select&nbsp;
                  <span style={{ color: "var(--primary-color)" }}>{countyQuantity}</span>&nbsp;more&nbsp;{countyQuantity > 1 ? "counties" : "county"}&nbsp;in {state}.
                </div>
                <div className="map-county-container">
                  <div className="map-county-box">
                    <CountySelector tier={level!}/>
                  </div>
                </div>
              </ClientSwipeAnimation>
            )}
          </AnimatePresence>
        </div>
      </>
      <Footer/>
    </SelectionContext.Provider>
  );
}

export default SelectionAgentView;
