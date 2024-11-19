import { ReactNode, useEffect } from 'react';
import { useLocation } from 'react-router-dom';

type ScrollToTopProps = {
  children: ReactNode;
};

const ScrollToTop = ({ children }: ScrollToTopProps): JSX.Element | null => {
  const { pathname } = useLocation();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, [pathname]);

  return <>{children}</> || null;
};

export default ScrollToTop;
