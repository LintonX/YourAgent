import CashIcon from '../src/assets/icons/Refund.svg'
import LocationIcon from '../src/assets/icons/location.svg'
import NotiIcon from '../src/assets/icons/notification.svg'
import ScheduleIcon from '../src/assets/icons/Schedule.svg'
import MagGlassIcon from '../src/assets/icons/SEO.svg'
import ClockIcon from '../src/assets/icons/time.svg'

import SearchButtonIcon from '../src/assets/icons/icons8-search.svg'

export const SERVER: string = "http://localhost:8080/";

export const BASIC_PRICE: string = "19";
export const STANDARD_PRICE: string = "28";
export const PREMIUM_PRICE: string = "59";

export const PRICE_TIER_MAP = {
    [BASIC_PRICE]: "basic",
    [STANDARD_PRICE]: "standard",
    [PREMIUM_PRICE]: "premium",
  };

export const COUNTY_QUANTITY_MAP: { [key: string]: number } = {
    "basic": 2,
    "standard": 3,
    "premium": 7,
  };

export const COUNTY_DATA_PATH: string = '../../Data/county_data.json'

export const SEARCH_BUTTON_ICON: string = SearchButtonIcon 

export type FeatureContent = {
    title: string;
    content: string;
    icon: string | undefined
};

export const FEATURES_CONTENT: Array<FeatureContent> = [
    {
        title: "Persistent Lead Flow", 
        content: "Get access to a consistent stream of local leads within your selected counties", 
        icon: ScheduleIcon
    }   ,
    {
        title: "Automated Lead Generation", 
        content: "YourAgent works in the background, consistently delivering leads to you without needing constant attention", 
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
        content: "There's no upfront cost or heavy commitment — just a stream of potential clients", 
        icon: CashIcon
    }
        ,
    {
        title: "Hassle-Free Client Matching", 
        content: "Focus more time on closing deals, not searching for clients", 
        icon: ClockIcon
    },
];

export type NavBarListItem = {
    label: string,
    sectionName?: string,
    shouldNavigateTo?: string
}

export const PRICING_VIEW_NAVBAR_ITEMS: Array<NavBarListItem> = [
    {
        label: 'How It Works',
        sectionName: 'row pricing-middle-container',
    },
    {
        label: 'Pricing',
        sectionName: 'row pricing-bottom-container',
    }
]

export const CLIENT_VIEW_NAVBAR_ITEMS: Array<NavBarListItem> = [
    {
        label: 'How It Works',
        sectionName: 'row client-bottom-container',
    },
    {
        label: 'Agents',
        shouldNavigateTo: 'Agents'
    }
];

export type ClientFeatureCardProps = {
    step: string,
    title: string,
    description: string,
};

export const CLIENT_VIEW_FEATURES_CONTENT: Array<ClientFeatureCardProps> = [
    {
        title: 'Choose a Location',
        description: 'Select an area where you\'d like to purchase or rent a property by typing in it\'s city and state.',
        step: '1'
    },
    {
        title: 'Share Quick Details',
        description: 'YourAgent just needs your preferred contact method to connect you with a local expert.',
        step: '2'
    },
    {
        title: 'Connect with YourAgent',
        description: 'We\'ll connect you with YourAgent. They will reach out to you shortly to discuss your home search goals.',
        step: '3'
    }
];