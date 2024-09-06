import UiImage from "@/assets/an_ui2.png";
import TagLine from "@/components/TagLine";

export default function HomeHero() {
    return (
        <div className="flex flex-col md:flex-row items-center justify-center h-screen">
            <TagLine />
            <img src={UiImage} className="w-2/5 h-h-full rounded-2xl shadow-lg shadow-red-600 hover:shadow-2xl hover:shadow-red-600 transition ease-in-out duration-300"></img>
        </div>
    );
}