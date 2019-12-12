package otto;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import Monsters.Elements;//������������� ������ Elements �� ��������� ������ Monsters

public class Programm {

	public static void main(String[] args) {
		
		

		String[] Names = {"�������","��������","����������","��������","�����","������","�������","�����",
				"�����","������","������","������","����","�������","���������","�����","����",
				"�����","�����","�������","�����","����","����","����","������"};

		Elements[] n = new Elements[Names.length]; //�������� ������� ���� Elements � ����������� �� ���������� ��������� ���
		Elements[] nReit = new Elements[Names.length]; //�������� ������� ���� Elements ��� ��������
		
		StringBuilder AtkOutput = new StringBuilder(); //���������� ��� ���������� ������ � ������ �����
		List<String> Statistic = new ArrayList<String>(); //������, ���� ����� ������������ ������ �� ������ �� ���������� AtkOutput
		
		for (int i=0; i<Names.length; i++) 
		{//���������� ��� �� ������� � ��������� ���������, � ���� ����� �� (0 �� 4)
			n[i]= new Elements(Names[i],(int)(Math.random()*5),(int)(Math.random()*5));//����� ������������ ��� ���������� �������� ��������� �������
		}

		int i,j, //���������� ����������� � ������������� �������
			Alive, //���������� ��� �������� ��������
			kick, //���������� ��� �������� �����
			job;//����������, ������� ������������� �������� ������������ ��� ��� 


		do { //������ ��������� ����� ��� ����������� �����
			
			Alive=0; //��������� �������� ��������
			for (Elements g : n) if (g.getHP()>0) Alive++; //������� �������� �� �������������� ����������� ��

				i=(int)(Math.random()*Names.length);
				j=(int)(Math.random()*Names.length); //��������� ����� ��� ������ ����������� � ������������� ����
			
				if ((i != j) && n[i].getHP()>0 && n[j].getHP()>0) //���� ������� �� �������� ����� ����� � ���� ��� ����
				{
						
			boolean fireSkill; //���������� ����� ������ �� �������
			do // ���� ���� ������ � ��������� fireSkill ����������� �� ��� ���, ���� �� �������� "�����" ����������� ����� �������� �����
			
			{ 	fireSkill=false;
			
				AtkOutput.delete(0, AtkOutput.capacity()); //��������� �������������, ������� ��������� ����� ���� �� �����
			
					kick=n[j].getHP(); //��������� �������� �� ������ �� ����� ��� �������� �����
						job=Elements.Attack(n[i], n[j]);//��������� ����� � ���������� �������� ����� ����������
							kick-=n[j].getHP();//���������� ���������� ������� � �� �� � ����� �����, �������� �������� �����
					
					AtkOutput.append(n[i].getName()+"."+n[i].getHP()+"("+n[i].getLvl()+") ����� �� "+n[j].getName()+"."+n[j].getHP()+"("+n[j].getLvl()+"), -"+kick+" ��.");

					if (job > 0) //���������� ������ ���������� ����� � ����������� �� ���� ��������� ����-����� ��������� ��� ���
						{
						AtkOutput.append(" (����� "+n[i].getElAtk()+"-"+n[j].getElDef()+")");
						}
					else if (job < 0) //������������ ������ ���������� ����� � ����������� �� ���� ��������� ������ ��������� ��� ���
						{
						AtkOutput.append(" (������ "+n[i].getElAtk()+"-"+n[j].getElDef()+")");
						}
					System.out.println(AtkOutput); // ����� ������ �������������� ������ �����
					
					Statistic.add(AtkOutput.toString()); //���������� ������ � "������" ���������� ������ ����, ��� ������
					
					if ((n[j].getHP()>0) && (n[j].getElDef()=="Fire") && n[j].Chancer(n[j].getChance()))
						//���� �������� �������� ������� � ����� � ����������� �hancer/����������� �����/, �� ���������� � ������ �������� �������,
						//� �������� ������������� �������� "������"
					{i^=j; j^=i; i^=j; // ������ �������� ������������� �������� ��� �������� ����������
					fireSkill=true; System.out.print("+��-");}
			}
			while (fireSkill); //��������� ����� ������ ����� �������� �����
			
				if (n[j].getHP()<=0) //���� �������, �� ������� ������ �������
					{
					System.out.println("# "+n[j].getName()+" �����! "+"�������� ("+(Alive-1)+")");
					nReit[Alive-1]=n[j]; //����� ������������� �������� � ������� ������, �� ��� ����������, ���������� ������������� �� ������
					Elements.lvlUp(n[i]); //���������� �������� ������ ���������� � ����� ���������� �� �����
					System.out.println("$$"+n[i].getName()+ " ������ ������� �� "+n[i].getLvl());
					}
				}
			}
		while (Alive>=2); //���������� ����� ���� �������� Alive ����
		
		System.out.println("________________________________________");//������ ���������� ������
		
		for (Elements g : n)
			if (g.getHP()>0)
			{ //������� �������� HP � ����� �� ����� ����� ����������
				System.out.println("����������: "+g.getName()+"!!! ");
				nReit[0]=g; //����� ������������� �������� ���������� ��� ����������� �������� �� ������
			}

		String sss;//���������� ��� ������ � ������ ������ � ����� � ������� �������� �� ������ ������� � ��������
		j = 1;//������������� �������� "1" ��� ���� ����� �� ��������� ���� "������ �����", ��������� ������������� ����������, ����������� � ������ ���������
		boolean mark = false;
		do
		{
			
			System.out.println("\n\t\t������� ������ (� ������� ���������):");

		i=0; //��������� i ��� ���������� ��� ������ � ������ ��������, ��������� ������������� ����������, ����������� � ������ ���������
		for (Elements g: nReit)
			{
			System.out.printf("#%2d",++i);
			g.ShowMonster(); //����� �� ����� ���� �������� � ������ � �������� ����� �����
			}
				
            if (j<=0 || j>(nReit.length)) JOptionPane.showMessageDialog(null,"������� ���������� ����� (1-"+nReit.length+")!", "������ �����", 0);
        sss = JOptionPane.showInputDialog(null, "������� ����� �������:", JOptionPane.INFORMATION_MESSAGE);
        if (sss==null || sss.equals("") || !sss.matches("[+-]?\\d+")) mark = false; //���� �������� �� ����� ��� �������� �������, ����������� �����
        	else
        	{
        	 j = Integer.parseInt(sss); mark=true; //������� ������ � �����, ����������� ��������� ����, ��� ��������� ������ ������� ����
         
        	 if (j>0 && j<=(nReit.length)) //���� ����� ������������� ��������� � ������� � ������� ��������
        	 {

        		 for (String s: Statistic)
        			 if ( s.contains(nReit[(j-1)].getName()))  //����� ������, ���������� � ���� ��� "�������"
        				 // && s.indexOf(nReit[(j-1)].getName()) == 0 //��� �����, ���� ������ ��� � ������, �.�. ������� ������ ����
        				 System.out.println(s);
        	 }
        	}
   		}
		while(mark);
	}

}