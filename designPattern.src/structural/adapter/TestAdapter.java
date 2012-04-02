//: structural.adapter.TestAdapter.java

package structural.adapter;

public class TestAdapter {
	public static void main(String[] args) {
		PC pc = new PC();
		USB usbmouse = new USBMouse();
		
		//pc.useMouse(mouse); // error
		
		PS2 adapter = new USBToPS2Adapter(usbmouse);
		pc.useMouse(adapter);
	}
}
interface PS2 {
	void usePs2();
}
interface USB {
	void useUsb();
}
class USBMouse implements USB {
	public void useUsb() {
		System.out.println("Use USB working");
	}
}

class USBToPS2Adapter implements PS2 {
	private USB usb;

	public USBToPS2Adapter(USB usb) {
		this.usb = usb;
	}
	public void usePs2() {
		System.out.println("ps2 mouse adapter to usb ");
		usb.useUsb();
	}
}
class PC {
	public void useMouse(PS2 ps2Mouse) {
		ps2Mouse.usePs2();
	}
}
