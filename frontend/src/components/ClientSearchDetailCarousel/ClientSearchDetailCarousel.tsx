import React, { useState, createContext, useEffect } from "react";
import { AnimatePresence } from "framer-motion";
import ClientSubmitUnit from "./ClientSubmitUnit/ClientSubmitUnit";
import ClientSearchUnit from "./ClientSearchUnit/ClientSearchUnit";
import ClientDetailUnit from "./ClientDetailUnit/ClientDetailUnit";
import ClientSwipeAnimation from "./ClientSwipeAnimation";

export type userInfo = {
  firstName: string;
  lastName?: string;
  email: string;
  phoneNumber?: string;
};

type ClientContextType = {
  place: string;
  setPlace: React.Dispatch<React.SetStateAction<string>>;
  county: string;
  setCounty: React.Dispatch<React.SetStateAction<string>>;
  userInfo: userInfo;
  setUserInfo: React.Dispatch<React.SetStateAction<userInfo>>;
  quickFact: string;
  setQuickFact: React.Dispatch<React.SetStateAction<string>>;
};

const defaultUserInfo = {
  firstName: "",
  lastName: "",
  email: "",
  phoneNumber: "",
};

export const ClientContext = createContext<ClientContextType>({
  place: "",
  setPlace: () => {},
  county: "",
  setCounty: () => {},
  userInfo: defaultUserInfo,
  setUserInfo: () => {},
  quickFact: "",
  setQuickFact: () => {}
});

function ClientSearchDetailCarousel() {
  const [place, setPlace] = useState<string>("");
  const [county, setCounty] = useState<string>("");
  const [userInfo, setUserInfo] = useState<userInfo>(defaultUserInfo);
  const [currentComponent, setCurrentComponent] = useState(0);
  const [quickFact, setQuickFact] = useState<string>("");

  useEffect(() => {
    if (county) {
      setCurrentComponent(1);
    }
  }, [county]);

  useEffect(() => {
    if (userInfo !== defaultUserInfo) {
      setCurrentComponent(2);
    }
  }, [userInfo]);

  return (
    <ClientContext.Provider
      value={{ place, setPlace, county, setCounty, userInfo, setUserInfo, quickFact, setQuickFact }}
    >
      <AnimatePresence mode="wait" initial={true}>
        {currentComponent === 0 && (
          <ClientSwipeAnimation key="search">
            <ClientSearchUnit />
          </ClientSwipeAnimation>
        )}
        {currentComponent === 1 && (
          <ClientSwipeAnimation key="detail">
            <ClientDetailUnit />
          </ClientSwipeAnimation>
        )}
        {currentComponent === 2 && (
          <ClientSwipeAnimation key="submitted">
            <ClientSubmitUnit />
          </ClientSwipeAnimation>
        )}
      </AnimatePresence>
    </ClientContext.Provider>
  );
}

export default ClientSearchDetailCarousel;
