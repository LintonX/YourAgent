import React, { PropsWithChildren, useEffect, useState } from "react";
import { motion } from "framer-motion";

function ClientSwipeAnimation({ children }: PropsWithChildren) {
  const [windowWidth, setWindowWidth] = useState<number>(window.innerWidth);

  useEffect(() => {
    const handleResize = () => setWindowWidth(window.innerWidth);
    window.addEventListener("resize", handleResize);

    return () => window.removeEventListener("resize", handleResize);
  }, []);

  const animation = {
    initial: { opacity: 0, x: windowWidth, filter: "blur(5px)" },
    animate: { opacity: 1, x: 0, filter: "blur(0px)" },
    exit: { opacity: 0, x: -windowWidth, filter: "blur(5px)" },
  };

  return (
    <motion.div
      variants={animation}
      initial="initial"
      animate="animate"
      exit="exit"
      transition={{ duration: .5, ease:"easeInOut" }}
    >
      {children}
    </motion.div>
  );
}

export default ClientSwipeAnimation;
