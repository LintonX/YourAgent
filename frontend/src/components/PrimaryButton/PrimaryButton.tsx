import React from 'react'
import '../../App.css'
import '../PrimaryButton/primaryButton.css'

type PrimaryButtonProps = {
  text: string;
  className: string;
  style?: React.CSSProperties;
  onClick: (event: React.MouseEvent<HTMLButtonElement>) => void | null;
};

function PrimaryButton({ style, text, onClick, className }: PrimaryButtonProps) {

  return (
    <button style={style} onClick={onClick} className={className}>{text}</button>
  )
}

export default PrimaryButton