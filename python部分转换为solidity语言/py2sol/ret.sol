pragma solidity ^0.4.18;
contract Main {
string __str = "";
uint __integer = 0;
uint __a;
function foo() public returns( uint ) {
__str = "global";
__integer = 10;
uint ret = 1 + 2 - 3 * 5;
if( __integer == 0 ){
 ret = 5;
}

return ret;

}

function bar(uint _a) public {
 _a = _a + 3;

}

}
