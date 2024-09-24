import React from 'react'
import '../../App.css'
import '../PrimaryButton/primaryButton.component.css'
import Button from 'react-bootstrap/Button';

type PrimaryButtonProps = {
  text: string;
  variant: string;
  className: string;
  onClick: (event: React.MouseEvent<HTMLButtonElement>) => void | null;
};

function PrimaryButton({ text, variant, className, onClick }: PrimaryButtonProps) {

  return (
    <Button variant={variant} className={className} onClick={onClick}>{text}</Button>
  )
}

export default PrimaryButton