import React from 'react';
import { motion } from 'framer-motion'
import '../../App.css';
import '../AIBubble/aiBubble.css';

export type AIBubbleProps = {
    quickFact: string
}

const animation = {
    initial: { opacity: 0},
    animate: { opacity: 1},
    exit: {opacity: 0}
};

function AIBubble({ quickFact }: AIBubbleProps) {
  return (
    <motion.div
      variants={animation}
      initial="initial"
      animate="animate"
      exit="exit"
      transition={{ duration: 1.1, ease:"easeIn", delay: .6 }}
    >
        <div className='ai-bubble-container'>
            <p className='quick-fact-text'>
                {quickFact}
            </p>
        </div>
    </motion.div>
  )
}

export default AIBubble