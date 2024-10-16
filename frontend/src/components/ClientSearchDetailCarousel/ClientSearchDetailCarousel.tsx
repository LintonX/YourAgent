import React, { useState, createContext, useEffect } from "react";
import { AnimatePresence } from "framer-motion";
import ClientCongratsUnit from "./ClientCongratsUnit/ClientCongratsUnit";
import ClientSearchUnit from "./ClientSearchUnit/ClientSearchUnit";
import ClientDetailUnit from "./ClientDetailUnit/ClientDetailUnit";
import ClientSwipeAnimation from "./ClientSwipeAnimation";

export type userInfo = {
  firstName: string;
  lastName?: string;
  email: string;
  phoneNumber?: string;
};

type CountyContextType = {
  county: string;
  setCounty: React.Dispatch<React.SetStateAction<string>>;
  userInfo: userInfo;
  setUserInfo: React.Dispatch<React.SetStateAction<userInfo>>;
};

const defaultUserInfo = {
  firstName: "",
  lastName: "",
  email: "",
  phoneNumber: "",
};

export const CountyContext = createContext<CountyContextType>({
  county: "",
  setCounty: () => {},
  userInfo: defaultUserInfo,
  setUserInfo: () => {},
});

function ClientSearchDetailCarousel() {
  const [county, setCounty] = useState<string>("");
  const [userInfo, setUserInfo] = useState<userInfo>(defaultUserInfo);
  const [currentComponent, setCurrentComponent] = useState(0);

  useEffect(() => {
    if (county) {
      setCurrentComponent(1);
    }
  }, [county]);

  useEffect(() => {
    if (userInfo !== defaultUserInfo) {
        setCurrentComponent(2)
    }
  }, [userInfo])

  return (
    <CountyContext.Provider
      value={{ county, setCounty, userInfo, setUserInfo }}
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
          <ClientSwipeAnimation>
            <ClientCongratsUnit></ClientCongratsUnit>
          </ClientSwipeAnimation>
        )}
      </AnimatePresence>
    </CountyContext.Provider>
  );
}

export default ClientSearchDetailCarousel;
