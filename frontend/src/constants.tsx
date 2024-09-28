import YourAgentSvg from '../src/assets/your-agent.svg'

import CashIcon from '../src/assets/icons/Refund.svg'
import LocationIcon from '../src/assets/icons/location.svg'
import NotiIcon from '../src/assets/icons/notification.svg'
import ScheduleIcon from '../src/assets/icons/Schedule.svg'
import MagGlassIcon from '../src/assets/icons/SEO.svg'
import ClockIcon from '../src/assets/icons/time.svg'

export const BASIC_PRICE: string = "19";
export const STANDARD_PRICE: string = "28";
export const PREMIUM_PRICE: string = "59";

export const COUNTY_DATA_PATH: string = '../../Data/county_data.json'

export type FeatureContent = {
    title: string;
    content: string;
    icon: string | undefined
};

export const FEATURES_CONTENT: Array<FeatureContent> = [
    {
        title: "Steady Lead Flow", 
        content: "Get access to a consistent stream of qualified leads within your selected counties", 
        icon: ScheduleIcon
    }   ,
    {
        title: "Automated Lead Generation", 
        content: "Our system works in the background, consistently delivering leads to you without needing constant attention", 
        icon: MagGlassIcon
    }
        ,
    {
        title: "County Farming", 
        content: "Choose 2, 3, or 7 counties to focus your lead generation efforts, tailored to your business needs", 
        icon: LocationIcon
    }
        ,
    {
        title: "Flexible Lead Alerts", 
        content: "Set your availability and receive lead notifications based on your schedule", 
        icon: NotiIcon
    }
        ,
    {
        title: "Low-Cost, Low-Risk", 
        content: "There's no upfront cost or heavy commitment—just a reliable flow of potential clients", 
        icon: CashIcon
    }
        ,
    {
        title: "Hassle-Free Client Matching", 
        content: "Focus more on closing deals, not searching for clients", 
        icon: ClockIcon
    },
];

export const YOUR_AGENT_SVG: string = YourAgentSvg;